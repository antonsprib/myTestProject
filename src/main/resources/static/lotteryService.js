function createLottery() {
    const title = document.getElementById('title').value;
    const limit = document.getElementById('limit').value;

    fetch('/start-registration', {
        method: 'POST',
        body: JSON.stringify({
            title: title,
            limit: limit
        }),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then((resp) => resp.json()
    ).then(response => {
        if (response.status === 'OK') {
            window.location.href = "/";
        } else {
            const div = document.createElement('div');

            div.innerHTML = `
        <td>${response.reason}</td>
`;
            document.getElementById('errors').appendChild(div);
        }
    });
}

function loadLotteries() {
    fetch("/lotteries",{
        method: "GET"
    }).then(
        resp => resp.json()
    ).then(lotteries => {
        for(const lottery of lotteries){
            addLotteries(lottery);
        }
    });
}

function addLotteries(lottery) {
    var newDate = new Date(lottery.startDate);
    var dd = newDate.getDate();

    var mm = newDate.getMonth()+1;
    var yyyy = newDate.getFullYear();
    if(dd<10)
    {
        dd='0'+dd;
    }

    if(mm<10)
    {
        mm='0'+mm;
    }
    newDate = dd+'.'+mm+'.'+yyyy;
    const tr = document.createElement("tr");
    var buttonDisable = lottery.lotteryStatus ==='OPEN' ? '' : 'disabled';

    tr.innerHTML = `
        <td>${lottery.title}</td>
        <td>${lottery.registeredParticipants} / ${lottery.limit}</td>
        <td>${newDate}</td>
        <td>${lottery.lotteryStatus}</td>
        
        <td class="text-right"><button type="button" class="btn btn-success" ${buttonDisable}  onclick="location.href='participan/registrationForm.html?lotteryId=${lottery.id}'">Register</a> </button> <button type="button" class="btn btn-danger" onclick="stopLottery(${lottery.id})">Stop lottery</button> <button type="button" class="btn btn-primary" onclick="chooseWinner(${lottery.id})">Choose winner</button></td>
    `;
    document.getElementById("table-body").appendChild(tr);
}

function stopLottery(lotId) {

    const id = lotId;

    fetch("/stop-registration", {
        method: 'POST',
        body: JSON.stringify({
            id: id,
    }),
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(() =>{
        window.location.href = "/";
    });

}

function chooseWinner(lotId){

    const id = lotId;

    fetch("/choose-winner", {
        method: 'POST',
        body: JSON.stringify({
            id: id,
        }),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(() =>{
        window.location.href = "/";
    });

}