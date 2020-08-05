var trList = $("table#plannedMeals > tbody > tr");

for (var i = 0; i < trList.length; i++) {
    var internalDateTime = trList[i].getElementsByClassName("internalDateTime")[0].innerText;

    // parse
    var splitted = internalDateTime.split(" ");
    var date = splitted[0];

    var mealType = "BREAKFAST";
    switch (splitted[1]) {
        case "07:00:00.0": {
            mealType = "Breakfast"
            break;
        }
        case "15:00:00.0": {
            mealType = "Snack"
            break;
        }
        case "12:00:00.0": {
            mealType = "Lunch"
            break;
        }
        case "19:00:00.0": {
            mealType = "Dinner"
            break;
        }

    }

    trList[i].getElementsByClassName("publicDate")[0].innerText     = date;
    trList[i].getElementsByClassName("publicMealType")[0].innerText = mealType;
}