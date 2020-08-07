var fun_date =function(n){
    let date1 = new Date()
    let milli = date1.getTime()+1000*60*60*24*n
    let date2 = new Date(milli);
    let year = date2.getFullYear()
    let month = date2.getMonth()+1
    let day = date2.getDate()
    let weekDay = date2.getDay()
    let hanWeekDay = handleWeek(weekDay)
    if(month.toString().length<2){
        month = '0' + month
    }
    if (day.toString().length<2){
        day = '0' + day
    }
    return {
        date:date2,
        year,
        month,
        day,
        weekDay,
        hanWeekDay
    }
}
function handleWeek(n){
    switch (n) {
        case 0:
            return '星期日';
        case 1:
            return '星期一';
        case 2:
            return '星期二';
        case 3:
            return '星期三';
        case 4:
            return '星期四';
        case 5:
            return '星期五';
        case 6:
            return '星期六';
    }
}
export default fun_date
