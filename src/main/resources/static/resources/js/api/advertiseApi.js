function addAdvertise() {
    let formData = new FormData();

    let filesRadio = document.querySelectorAll('input[type="radio"]');
    let thumbNail = '';

    for (let radio of filesRadio) {
        if (radio.checked) thumbNail = radio.value;
    }

    let files = document.getElementById('formFile').files;
    for (let i = 0; i < files.length; i++) {
        formData.append("files", files[i]);
    }

    let requestData = {
        'title': document.getElementById('advertiseTitle').value,
        'address': document.getElementById('advertisePlacement').value,
        'description': document.getElementById('advertiseDescription').value,
        'thumbName': thumbNail,
        'type': document.getElementById('advertiseType').value,
        'square': document.getElementById('advertiseSquare').value,
        'price': document.getElementById('advertisePrice').value,
        'bedroom': document.getElementById('bedroomCount').value,
        'bathroom': document.getElementById('bathroomCount').value
    };

    formData.append("advertise", new Blob(
        [JSON.stringify(requestData)], {
            type: 'application/json'
        })
    );

    fetch('/api/advertise', { method: 'POST', body: formData })
        .then(async response => {
            let payload = response.status;
            if (payload === 200) {
                location.reload();
            }
        });
}

function updateAdvertise() {
    let advertiseId = window.location.href.split('id=')[1];

    let requestData = {
        'title': document.getElementById('advertiseTitle').value,
        'address': document.getElementById('advertisePlacement').value,
        'description': document.getElementById('advertiseDescription').value,
        'square': document.getElementById('advertiseSquare').value,
        'price': document.getElementById('advertisePrice').value,
        'bedroom': document.getElementById('bedroomCount').value,
        'bathroom': document.getElementById('bathroomCount').value
    };

    fetch(`/api/advertise/${advertiseId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(requestData) })
        .then(async response => {
            let payload = response.status;
            if (payload === 200) {
                location.reload();
            }
        });
}

function deleteAdvertise(element) {
    let advertiseId = element.id.split('-')[1];
    fetch(`/api/advertise/${advertiseId}`, { method: 'DELETE' })
        .then(async response => {
            let payload = response.status;
            if (payload === 200) {
                location.reload();
            }
        });
}