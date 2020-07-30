$('.addToPlanForm').submit(function (e) {
    e.preventDefault();

    $.ajax({
        method: 'POST',
        url: 'all?addToPlan',
        data: $(this).serialize(),
        success: function (data) {
            console.log("Successfully added.")
            //callback methods go right here
        },
        error: function (req, err) {
            console.error('Error: ' + err);
        }
    });

    return false;
});