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
            <button type="button" class="btn btn-success" ${registerButtonDisable}  onclick="location.href='participan/registrationForm.html?lotteryId=${lottery.id}'">Register</a> </button> 
            <button type="button" class="btn btn-primary"  onclick="location.href='lotteries/getStatus.html?id=${lottery.id}'">Status</button>                            
        </td>
    `;
    document.getElementById("table-body").appendChild(tr);
}

function getStatus(){
    const email = document.getElementById('email').value;
    const code = document.getElementById('uniqueCode').value;
    const id = new URL(window.location.href).searchParams.get('id');

    fetch('/status?id=' + id + '&email=' + email + '&code=' + code, {
        method: 'GET'
        }).then((resp) => resp.json()
    ).then(response => {
        alert(response.status);
    });

}
