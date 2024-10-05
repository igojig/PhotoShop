
function disablePanel(id) {
    var panel = document.getElementById(id);
    var inputs = panel.querySelectorAll('input, button'); //anything else can go in here
    for (var i=0; i<inputs.length; i++) {
        inputs[i].disabled = true;
    }
     //  panel.style.opacity = 0.3; //or any other value
}
