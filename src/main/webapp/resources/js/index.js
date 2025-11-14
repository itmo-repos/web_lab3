function updateClock() {
    fetch('getTime')
        .then(response => response.text())
        .then(data => {
            let hour = document.getElementById("hour");
            let minute = document.getElementById("minute");
            let seconds = document.getElementById("seconds");
            
            const serverDate = new Date(data);
            let hr = serverDate.getHours();
            let min = serverDate.getMinutes();
            let sec = serverDate.getSeconds();

            let calc_hr = (hr * 30) + (min / 2);
            let calc_min = (min * 6) + (sec / 10);
            let calc_sec = sec * 6;

            hour.style.transform = `rotate(${calc_hr}deg)`;
            minute.style.transform = `rotate(${calc_min}deg)`;
            seconds.style.transform = `rotate(${calc_sec}deg)`;

            const dateElement = document.getElementById('date');
            const options = {year: 'numeric', month: 'long', day: 'numeric' };
            dateElement.textContent = serverDate.toLocaleDateString('ru-RU', options);
        })
}

document.addEventListener('DOMContentLoaded', () => {
    updateClock();
    setInterval(updateClock, 8000);
});
