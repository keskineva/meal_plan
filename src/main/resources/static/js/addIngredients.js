function add_fields() {
    document.getElementById("myTable").insertRow(-1).innerHTML = '<tr><td><textarea name ="Ingredient" placeholder="Ingredient" ' +
        'id="ingredient" ' +
        'style = "resize: none; width:100%;"></textarea></td>' +
        '<td><textarea name="Amount" placeholder ="Amount" ' +
        ' id="amount" style="resize:none;width: 100%;"></textarea></td ></tr>';
}