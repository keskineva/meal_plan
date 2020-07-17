function add_fields() {
    document.getElementById("myTable").insertRow(-1).innerHTML = '<tr><td><textarea name ="ingredients" placeholder="Ingredient" ' +
        'id="ingredient" ' +
        'style = "resize: none; width:100%;"></textarea></td>' +
        '<td><textarea name="amounts" placeholder ="Amount" ' +
        ' id="amount" style="resize:none;width: 100%;"></textarea></td ></tr>';
}