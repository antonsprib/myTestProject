function registerParticipan() {


    const email = document.getElementById('email').value;
    const age = document.getElementById('age').value;
    const uniqueCode = document.getElementById('uniqueCode').value;
    const lotteryId = new URL(window.location.href).searchParams.get("lotteryId");

    fetch('/register', {
        method: 'POST',
        body: JSON.stringify({
            lotteryId: lotteryId,
            email: email,
            age: age,
            uniqueCode: uniqueCode
        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then((resp) => resp.json()
    ).then(response => {
        if (response.status === 'OK') {
            window.location.href = "/";
        } else {
            alert(response.reason);
        }
    });

}