$('.addToPlanForm').submit(function(e){
    e.preventDefault();
    //do some verification
    $.ajax({
        url: 'recipes/all?addToPlan',
        data: $(this).serialize(),
        success: function(data)
        {
            //callback methods go right here
        }
    });
});