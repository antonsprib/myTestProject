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
            window.location.href = "/admin/index.html";
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
    const tr = document.createElement("tr");
    var registerButtonDisable = lottery.lotteryStatus ==='OPEN' || lottery.registeredParticipants >= lottery.limit? '' : 'disabled';
    var stopButtonDisable = lottery.lotteryStatus != 'OPEN' ? 'disabled' : '';
    var winnerButtonDisable = lottery.lotteryStatus != 'CLOSED' ? 'disabled' : '';

    tr.innerHTML = `
        <td>${lottery.title}</td>
        <td>${lottery.registeredParticipants} / ${lottery.limit}</td>
        <td>${lottery.startDate}</td>
        <td>${lottery.lotteryStatus}</td>
        
        <td class="text-right">
             <button type="button" class="btn btn-danger" ${stopButtonDisable} onclick="stopLottery(${lottery.id})">Stop lottery</button> 
             <button type="button" class="btn btn-primary" ${winnerButtonDisable} onclick="chooseWinner(${lottery.id})">Choose winner</button>
        </td>
    `;
    document.getElementById("table-body").appendChild(tr);
}



function stopLottery(lotId) {

    const id = lotId;

    fetch("/stop-registration", {
        method: 'POST',
        body: JSON.stringify({
            id: id
    }),
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(() =>{
        window.location.href = "/admin/index.html";
    });

}



function chooseWinner(lotId){

    const id = lotId;

    fetch("/choose-winner", {
        method: 'POST',
        body: JSON.stringify({
            id: id
        }),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(() =>{
        window.location.href = "/admin/index.html";
    });

}



function loadStatistic() {
    fetch("/stats",{
        method: "GET"
    }).then(
        resp => resp.json()
    ).then(statistics => {
        for(const statistic of statistics){
            addStatistics(statistic);
        }
    });
}



function addStatistics(statistic) {

    const tr = document.createElement("tr");

    tr.innerHTML = `
        <td>${statistic.id}</td>
        <td>${statistic.title}</td>
        <td>${statistic.startDate}</td>
        <td>${statistic.endDate}</td>
        <td>${statistic.registeredParticipants}</td>
    `;
    document.getElementById("table-body").appendChild(tr);
}

