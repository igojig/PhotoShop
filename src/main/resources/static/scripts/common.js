
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

 /* Globle variables */
      var totalFileCount, fileUploadCount, fileSize, successCount;

      /* start uploading files */
      function startUploading() {
          var files = document.getElementById('files').files;
          if(files.length==0){
              alert("Please choose at least one file and try.");
              return;
          }
          fileUploadCount=0;
          successCount = 0;
          prepareProgressBarUI(files);

          // upload through ajax call
          uploadFile();
      }

      /* This method will be called to prepare progress-bar UI */
      function prepareProgressBarUI(files){
          totalFileCount = files.length;
          var $tbody=$("#progress-bar-container").find("tbody");
          $tbody.empty();
          $("#upload-header-text").html("1 of "+totalFileCount+" file(s) is uploading");
          for(var i=0;i<totalFileCount;i++){
              var fsize=parseFileSize(files[i].size);
              var fname=files[i].name;
              var bar='<tr id="progress-bar-'+i+'"><td style="width:75%"><div class="filename">'+fname+'</div>'
              +'<div class="progress"><div class="progress-bar progress-bar-striped active" style="width:0%"></div></div><div class="error-msg text-danger"></div></td>'
              +'<td  style="width:25%"><span class="size-loaded"></span> '+fsize+' <span class="percent-loaded"></span></td></tr>';
              $tbody.append(bar);
          }
          $("#upload-status-container").show();
      }

      /* parse the file size in kb/mb/gb */
      function parseFileSize(size){
          var precision=1;
          var factor = Math.pow(10, precision);
          size = Math.round(size / 1024); //size in KB
          if(size < 1000){
              return size+" KB";
          }else{
              size = Number.parseFloat(size / 1024); //size in MB
              if(size < 1000){
                  return (Math.round(size * factor) / factor) + " MB";
              }else{
                  size = Number.parseFloat(size / 1024); //size in GB
                  return (Math.round(size * factor) / factor) + " GB";
              }
          }
          return 0;
      }

      /* one by one file will be uploaded to the server by ajax call*/
      function uploadFile() {
          var file = document.getElementById('files').files[fileUploadCount];
          fileSize = file.size;
          var xhr = new XMLHttpRequest();
          var fd = new FormData();
          fd.append("multipartFile", file);
          fd.append("eventId", document.getElementById('eventId').value);
          xhr.upload.addEventListener("progress", onUploadProgress, false);
          xhr.addEventListener("load", onUploadComplete, false);
          xhr.addEventListener("error", onUploadError, false);
          xhr.open("POST", "/images/upload");
          xhr.send(fd);

      }

      /* This function will continueously update the progress bar */
      function onUploadProgress(e) {
          if (e.lengthComputable) {
              var loaded = e.loaded;
              var percentComplete = parseInt((loaded) * 100 / fileSize);
              if(percentComplete < 100){
                  var pbar = $('#progress-bar-'+fileUploadCount);
                  var bar=pbar.find(".progress-bar");
                  var sLoaded=pbar.find(".size-loaded");
                  var pLoaded=pbar.find(".percent-loaded");
                  bar.css("width",percentComplete + '%');
                  sLoaded.html(parseFileSize(loaded)+ " / ");
                  pLoaded.html("("+percentComplete+ "%)");
              }
          } else {
              alert('unable to compute');
          }
      }

      /* This function will call when upload is completed */
      function onUploadComplete(e, error) {
          var pbar = $('#progress-bar-'+fileUploadCount);
          var bar = pbar.find(".progress-bar");
          if(e.currentTarget.responseText!="" || error){
              bar.removeClass("active").addClass("progress-bar-danger");
              pbar.find(".error-msg").html(e.currentTarget.responseText || "Something went wrong!");
          }else{
              bar.removeClass("active");
              bar.css("width",'100%');
              var sLoaded=pbar.find(".size-loaded");
              var pLoaded=pbar.find(".percent-loaded");
              sLoaded.html('<i class="fa fa-check text-success"></i> ');
              pLoaded.html("(100%)");
              successCount++;
          }
          fileUploadCount++;
          if (fileUploadCount < totalFileCount) {
              //ajax call if more files are there
              uploadFile();
              $("#upload-header-text").html((fileUploadCount+1)+" of "+totalFileCount+" file(s) is uploading");
          } else if(successCount==0){
              $("#upload-header-text").html("Error while uploading");
          } else{
              $("#upload-header-text").html(successCount+" of "+totalFileCount+" file(s) uploaded successfully");
          }
      }

      /* This function will call when an error come while uploading */
      function onUploadError(e) {
          console.error("Something went wrong!");
          onUploadComplete(e,true);
      }

      function showHide(ele){
          if($(ele).hasClass('fa-window-minimize')){
              $(ele).removeClass('fa-window-minimize').addClass('fa-window-restore').attr("title","restore");
              $("#progress-bar-container").slideUp();
          }else{
              $(ele).addClass('fa-window-minimize').removeClass('fa-window-restore').attr("title","minimize");
              $("#progress-bar-container").slideDown();
          }
      }

      function divideByPerformances(){

//        let root=document.getElementById('performancesBlock');
        let elements=document.getElementsByClassName('form-check-input');


        let event={
            eventId: document.getElementById('eventId').value,
            performancesNumbers: []
        };

        for (let elem of elements){
            if(elem.checked==true){
                 event.performancesNumbers.push(elem.value);
            }
        }

//        if(event.performancesNumbers.length==0){
//            alert("Необходимо выбрать номера");
//        }

        return event;
//        var xhr = new XMLHttpRequest();
//        xhr.open("POST", "/images/selectedPerformances");
//        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
//        xhr.send(JSON.stringify(event));
      }

      function checkNumbers(){
        let obj=divideByPerformances();
        if(obj.performancesNumbers.length!=0){
            $('#btnProcess').prop('disabled', false);
        } else {
            $('#btnProcess').prop('disabled', true);
        }
      }
