const imageModal = new bootstrap.Modal('#imageViewModal');
const imageElement = document.querySelector('#fullImage')

function setImage(element) {
    let imageSrc = element.getAttribute('src');
    imageElement.setAttribute('src', imageSrc);
    imageModal.show();
}