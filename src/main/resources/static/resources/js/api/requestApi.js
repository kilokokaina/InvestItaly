function createRequest() {
    const successModal = new bootstrap.Modal('#successModal');
    let advertiseId = window.location.href.split('advertise/')[1].replace('#', '');

    let requestData = {
        'customerName': document.querySelector('#customerName').value,
        'customerEmail': document.querySelector('#customerEmail').value,
        'customerPhone': document.querySelector('#customerPhone').value,
        'advertiseId': advertiseId
    }

    fetch('/api/request', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(requestData)
    }).then(async response => {
        let payload = response.status;
        if (payload === 200) {
            successModal.show();
        }
    })
}