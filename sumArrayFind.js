function compareNumbers(a, b) {
  return a - b
}

function sortArray(arr) {
    return arr.sort(compareNumbers)
}
/*
function sumContaine(x, arr) {
    if (arr.length < 3) return false
    
    var newArr = sortArray(arr)
    var length = newArr.length
    
    for(var i = 0; i < length - 2; i++) {
        for(var j = i + 1; j < length - 1; j++) {
            for(var k = j + 1; k < length; k++) {
                if (newArr[i] + newArr[j] + newArr[k] === x) {
                    return true
                }
            }
        }
    }    
    return false
}
*/
