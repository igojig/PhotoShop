var fileInput = document.querySelector('#files');
var uploadButton = document.querySelector('#uploadButton');
var uploadUrl = '/images/upload';
var currentFileIndex = 0;
var errorCount = 0;
var successCount = 0;


uploadButton.addEventListener('click', (event) => {
    console.log('uploadCLick', event);
    uploadFetch(event);
})

fileInput.addEventListener('input', (event) => {

    if (event.target.files.length > 0) {
        handleFiles(event.target.files);
    } else {
        handleCancel();
    }
    prepare();
});

fileInput.addEventListener('cancel', evt => {
    handleCancel();
})

function handleFiles(files) {
    let totalFilesLength = 0;
    for (file of files) {
        totalFilesLength += file.size;
    }
    document.querySelector('#totalFilesSize').hidden = false;
    document.querySelector('#totalFilesSize').innerHTML = `Размер: ${totalFilesLength.toLocaleString("en-US")} байт`;
}

function handleCancel() {
    console.log('Cancel');
    document.querySelector('#totalFilesSize').hidden = true;
    document.querySelector('#totalFilesSize').innerHTML='';
}

function uploadFetch(event) {
    if (fileInput.files.length === 0) {
        alert('Файлы не выбраны');
        return;
    }
    totalFileCount = 0;

    prepare();

    processUpload();

}

function prepare() {
    currentFileIndex = 0;
    successCount=0;
    errorCount=0;
    document.querySelector('#uploadInfo').hidden = false;
    document.querySelector('.progress-bar').style.cssText='width: 0%;';
    document.querySelector('#successCount').innerHTML=`Загружено 0 из ${fileInput.files.length}`;
    document.querySelector('#errorCount').innerHTML='';
    document.querySelector('#uploadStatistics').innerHTML='';
}

function processUpload() {

    if (currentFileIndex === fileInput.files.length) {
        alert('Выгрузка завершена');
        return;
    }

    let data = new FormData();
    data.append('multipartFile', fileInput.files[currentFileIndex])
    data.append('eventId', document.querySelector('#eventId').value)
    fetch(uploadUrl, {
        method: 'POST',
        body: data
    }).then(resp => {
        console.log('response');
        if (resp.ok) {
            processOk(resp.json());
        } else {
            processError(resp.json());
        }
    })
        .catch(err => {
            console.log(err);
            let rowError=`
            <div class="row mb-1">
                <div class="col-sm">${err}</div>
            </div> 
        `;
            document.querySelector('#uploadStatistics').insertAdjacentHTML('beforeend', rowError);
        })
        .finally(() => {
            console.log('finally');
            currentFileIndex++;
            updateProgress();
            processUpload();
        })
}

function processOk(resp) {
    console.log('Response section');

    resp.then(data => {

        console.log(data)
        successCount++;
        document.querySelector('#successCount').innerHTML = `Загружено: ${successCount} из ${fileInput.files.length}`;
        let rowOk=`
            <div class="row mb-1">
                <div class="col-sm-2">${data.imageId}</div>
                <div class="col-sm-2">${data.fileName}</div>
            </div> 
        `;
        document.querySelector('#uploadStatistics').insertAdjacentHTML('beforeend', rowOk);
        console.log('End response');
    });
}

function processError(resp) {

    resp.then(data => {

        console.log(data);
        errorCount++;
        document.querySelector('#errorCount').innerHTML = `Ошибок: ${errorCount}`;
        let errorRow=`
            <div class="row mb-1">
                <div class="col-sm-2">${data.imageId}</div>
                <div class="col-sm-2">${data.fileName}</div>
                <div class="col-sm">${data.message}</div>
            </div>
        `;
        document.querySelector('#uploadStatistics').insertAdjacentHTML('beforeend', errorRow);
    });

}

function updateProgress() {
    let value = (currentFileIndex / fileInput.files.length * 100).toFixed(2);
    let txtStyle = `width: ${value}%;`
    document.querySelector('.progress-bar').style.cssText = txtStyle;
}