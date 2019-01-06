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
    }).then(() => {
        window.location.href = '/';
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
    newDate = mm+'.'+dd+'.'+yyyy;
    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${lottery.title}</td>
        <td>${lottery.limit}</td>
        <td>${newDate}</td>
        <td>${lottery.lotteryStatus}</td>
        <td class="text-right"><button type="button" class="btn btn-success">Register</button> <button type="button" class="btn btn-danger">Stop lottery</button> <button type="button" class="btn btn-primary">Choose winner</button></td>
    `;
    document.getElementById("table-body").appendChild(tr);

}
