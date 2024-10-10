
function disablePanel(id) {
    var panel = document.getElementById(id);
    if(panel != null){
        var inputs = panel.querySelectorAll('input, button'); //anything else can go in here
        for (var i=0; i<inputs.length; i++) {
            inputs[i].disabled = true;
        }
    }
     //  panel.style.opacity = 0.3; //or any other value
}

function setActiveLink(event){
    let currentLink = document.querySelector('[aria-selected=true]');
                                  if(currentLink!=null){
                                    currentLink.setAttribute('aria-selected', 'false');
                                    currentLink.classList.remove('active');
                                  }
                                    let newLink = event.target;
                                    newLink.setAttribute('aria-selected', 'true');
                                    newLink.classList.add('active');
}
