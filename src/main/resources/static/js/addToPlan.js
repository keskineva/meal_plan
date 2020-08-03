$('.addToPlanForm').submit(function (e) {
    e.preventDefault();

    $.ajax({
        method: 'POST',
        url: 'all?addToPlan',
        data: $(this).serialize(),
        success: function (data) {
            console.log("Successfully added: " + data)
            //callback methods go right here
            var successDiv = e.currentTarget.parentElement.querySelector(".addToPlanResult");
            successDiv.innerHTML = "Meal added to plan successfully.";
            successDiv.removeAttribute("hidden");
        },
        error: function (req, err) {
            console.error('Error: ' + err);
            var errorDiv = e.currentTarget.parentElement.querySelector(".addToPlanResultErr");
            errorDiv.innerHTML = "Ops, something went wrong. :(";
            errorDiv.removeAttribute("hidden");
        }
    });

    return false;
});