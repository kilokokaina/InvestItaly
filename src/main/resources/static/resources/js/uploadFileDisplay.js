function displayFiles() {
    let files = document.querySelector('#formFile').files;
    let listDiv = document.querySelector('#file-list');

    listDiv.innerHTML = '';

    for (let i = 0; i < files.length; i++) {
        listDiv.innerHTML +=
            `<div class="form-check">
                <input class="form-check-input" type="radio" name="isThumbnail" value="${files[i].name}" id="flexRadioDefault${i}">
                <label class="form-check-label" for="flexRadioDefault${i}">${files[i].name}</label>
            </div>`
        ;
    }
}