// BUILD TRIGGERS BY MODULE
// 2017-05-08 SLEFFERTS v1.0 - INITIAL BUILD
// 2017-05-16 NCHOUDHURY/SLEFFERTS v1.1 - Rebuild

var fs = require('fs');
var {csvLine, tsvLine, Frame, innerJoin, leftJoin, arrDedup, project, sortFrameBy, groupByToDict} = require("matcher");
var {readFrame, writeFrame, genTSV} = require("./scriptUtils");


let finalCodeCols = "MODULE,RULE_TYPE,ACTION,CLAIM_TYPE,CLAIM_SETTING,PRIMARY,ANY,ICD9,ICD10,SERVICE_CODE,MODIFIER,SPECIFICITY,POS,BILL_TYPE,AGE,GENDER,ADDON,DRG".split(",");
let finalCols = "MODULE,RULE_TYPE,ACTION,CLAIM_TYPE,CLAIM_SETTING,PRIMARY,ANY,SERVICE_CODE,MODIFIER,SPECIFICITY,POS,BILL_TYPE,AGE,GENDER,ADDON,DRG".split(",");

console.log("Loading files...");
// MODULE	ICD10	TYPE	I10_DESC	ICD9	I9_DESC	PATTERN
var MIF = readFrame("data/module_icd_map.tsv", tsvLine);
// MODULE	PRIMARY	ANY	SERVICE_CODE	AGE	GENDER	CLAIM_TYPE	CLAIM_SETTING	MODIFIER	BILAT_REDO	POS	BILL_TYPE
var MC = readFrame("data/OptimizedCredit.txt", tsvLine);
// ACRONYM	SERVICE_CODE	CLAIM_TYPE	CLAIM_SETTING	ACTION	SPECIFICITY	ADDON_PERIOD
var MD = readFrame("data/source_data/ModuleDebit_118.txt", tsvLine);
// Acronym	Primary Diagnosis	Any Diagnosis	Action	Claim Type	Claim Setting
var ME = readFrame("data/source_data/ModuleEnabler_118.txt", tsvLine);

ME._columns = ["ACRONYM","PRIMARY","ANY","ACTION","CLAIM_TYPE","CLAIM_SETTING"];

// console.log("MC:",MC);

console.log("Processing...");

console.log("Credits");
// dx
let DX = MIF.filter(v => v.TYPE === 'CM');
let newCols = "1.MODULE	PRIMARY	ANY	SERVICE_CODE	AGE	GENDER	CLAIM_TYPE	CLAIM_SETTING	MODIFIER	POS	BILL_TYPE	ICD9	ICD10".split("\t");
let JF = leftJoin(MC, DX, newCols, "MODULE==MODULE", (row1,row2) => row1[1] === row2[6] || row1[2] === row2[6]);
let JFS = JF.sort(['MODULE','ICD9','ICD10']);
// console.log("JFS:",JFS);

newCols = "1.MODULE	PRIMARY	PATTERN=ANY	SERVICE_CODE	AGE	GENDER	CLAIM_TYPE	CLAIM_SETTING	MODIFIER	POS	BILL_TYPE	1.ICD9	1.ICD10".split("\t");
let JF2 = leftJoin(DX, MC, newCols, "MODULE==MODULE", (row1,row2) => row1[6] === row2[1] || row1[6] === row2[2]);

// px
let PX = MIF.filter(v => v.TYPE === 'PCS' || v.TYPE === 'SC');
// console.log("PX:",PX);
let data = PX.map( row => 
	[row.MODULE,
	"",
	"",
	row.PATTERN,
	"",
	"",
	"",
	"",
	"",
	"",
	"",
	"",
	""
	]);
// console.log("DATA:",data);

JFS.data = JFS.data.concat(JF2.data,data);
JFS.data = arrDedup(JFS.data);
JFS = JFS.sort(['MODULE','ICD9','ICD10']);

// console.log("JFS:",JFS.filter(v => !v.MODULE));

function getClaimType(m,ct) {
	let modType = m.substr(-1);
	if (ct === 'UB92' || ct === 'UB' || modType === '2')
		return 'UB';
	if (ct === 'BOTH' || modType === '1')
		return 'UB-HCFA';
	if (ct === 'HCFA') return ct;
	else return modType === '2' ? 'UB' : 'UB-HCFA';
}

function getClaimSetting(m,cs) {
	let modType = m.substr(-1);
	if (cs === 'BOTH' || modType === '1')
		return 'IP-OP';
	if (cs === 'INPT' || modType === '2')
		return 'IP';
	if (cs === 'OUTPT' || cs === 'OP') return 'OP';
	else return modType === '2' ? 'IP' : 'IP-OP';
}

//JFS.map( (row,ix) => {if(!row.MODULE) console.log(ix, row)});

data = JFS.map( (row,ix) =>  {
	    if(!row.MODULE) console.log(ix, row);
		return [
		row.MODULE,
		"CREDIT",
		"ADD",
		row.CLAIM_TYPE = getClaimType(row.MODULE,row.CLAIM_TYPE),
		row.CLAIM_SETTING = getClaimSetting(row.MODULE,row.CLAIM_SETTING),
		row.MODULE.substr(-1) === '2' ? (!row.PRIMARY ? row.ANY : row.PRIMARY) : '',
		row.MODULE.substr(-1) === '1' ? row.ANY : '',
		row.ICD9,
		row.ICD10,
		row.SERVICE_CODE,
		row.MODIFIER,
		"",
		row.POS,
		row.BILL_TYPE,
		row.AGE,
		row.GENDER,
		"",
		""
		]; }
	
);

JFS.data = data;
JFS._columns = finalCodeCols;
console.log("Count2:",data.length);
// console.log("JFS:",JFS);


console.log("Enablers ");
let eCols = ["ACRONYM=MODULE","PRIMARY","ANY","ACTION","CLAIM_TYPE","CLAIM_SETTING","ICD9","ICD10"];
// Acronym	Primary Diagnosis	Any Diagnosis	Action	Claim Type	Claim Setting
let EF = leftJoin(ME, DX, eCols, "ACRONYM==MODULE", (row1,row2) => row1[1] === row2[6] || row1[2] === row2[6]);
let EFS = EF.sort(['MODULE','ICD9','ICD10']);

data = EFS.map( row => 
	[
	row.MODULE,
	"ENABLER",
	row.ACTION === 'I' ? 'ADD' : 'REMOVE',
	row.CLAIM_TYPE === 'UB92' ? 'UB' : (row.CLAIM_TYPE === 'BOTH' ? 'UB-HCFA' : row.CLAIM_TYPE),
	row.CLAIM_SETTING === 'BOTH' ? 'IP-OP' : row.CLAIM_SETTING === 'INPT' ? 'IP' : 'OP',
	row.PRIMARY,
	row.ANY,
	row.ICD9,
	row.ICD10,
	"",
	"",
	"",
	"",
	"",
	"",
	"",
	""
	]
);
console.log("Count:",data.length);
JFS.data = JFS.data.concat(data);


console.log("Debits");

// let dCols = "ACRONYM	SERVICE_CODE	CLAIM_TYPE	CLAIM_SETTING	ACTION	SPECIFICITY	ADDON_PERIOD".split("\t");
data = MD.map( row => 
	[
	row.ACRONYM,
	"DEBIT",
	row.ACTION === 'I' ? 'ADD' : 'REMOVE',
	row.CLAIM_TYPE === 'UB92' ? 'UB' : (row.CLAIM_TYPE === 'BOTH' ? 'UB-HCFA' : row.CLAIM_TYPE),
	row.CLAIM_SETTING === 'BOTH' ? 'IP-OP' : row.CLAIM_SETTING === 'INPT' ? 'IP' : 'OP',
	"",
	"",
	"",
	"",
	row.SERVICE_CODE,
	"",
	row.SPECIFICITY === 'N' ? 'NON-SPECIFIC' : (row.SPECIFICITY === 'H' ? 'HIGHLY' : 'SPECIFIC'),
	"",
	"",
	"",
	"",
	row.ADDON_PERIOD,
	""
	]
);
console.log("Count:",data.length);
JFS.data = arrDedup(JFS.data.concat(data));

console.log("Sorting Final Frame");
JFS = JFS.sort(["MODULE","RULE_TYPE","ICD9","ICD10"]);
console.log("JFS",JFS.data.length);

console.log("Writing file trigger_codes");
writeFrame("./data/trigger_codes.tsv", JFS, genTSV);

JFS = JFS.project(finalCols);
JFS.data = arrDedup(JFS.data);

console.log("Writing file triggers");
writeFrame("./data/triggers.tsv", JFS, genTSV);

console.log("Done");
