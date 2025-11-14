function checkHit(x, y, r) {
  if (x > 0 && y > 0) {
    return false;
  } else if (x <= 0 && y >= 0) {
    return y <= r / 2.0 && x >= -r;
  } else if (x < 0 && y < 0) {
    return (x**2 + y**2) <= (r/2.0)**2;
  } else {
    return -y <= r - (x * 2.0);
  }
}

function getSelectedR() {
    const radios = document.getElementsByName('main-form:r');
    
    for (let i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
    return null;
}

function redrawChart() {
    const svg = document.getElementById("svg-chart");
    if (svg) {
        const rValue = parseFloat(getSelectedR());
        if (Number.isNaN(rValue)) {
            return;
        }

        fetch('getPoints')
            .then(response => response.json())
            .then(points => {
                clearPoints();
                
                points.forEach((point_data) => {
                    const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
                    point.setAttribute("cx", point_data.x/rValue*120.0 + 150);
                    point.setAttribute("cy", 150 - point_data.y/rValue*120.0);
                    point.setAttribute("r", "2");
                    point.setAttribute("class", checkHit(point_data.x, point_data.y, parseFloat(rValue)) ? "point-hit" : "point-miss");
                    svg.appendChild(point);
                });
            })
            .catch(err => console.error('Ошибка загрузки точек:', err));
    }
}

function clearPoints() {
    const svg = document.getElementById("svg-chart");
    if (svg) {
        const points = svg.querySelectorAll('circle');
        points.forEach(point => {
            point.remove();
        });
    }
}


function showFormError(message) {
    const sendBtn = document.getElementById('button-panel');
    if (!sendBtn) return;
    let errorEl = document.getElementById('form_error');
    if (!errorEl) {
        errorEl = document.createElement('div');
        errorEl.id = 'form_error';
        errorEl.style.color = 'red';
        errorEl.className = 'form-group';
        if (sendBtn.parentNode) {
            sendBtn.parentNode.insertBefore(errorEl, sendBtn);
        }
    }
    errorEl.textContent = message;
}

function clearFormError() {
    const errorEl = document.getElementById('form_error');
    if (errorEl) errorEl.remove();
}


function showPoint(x, y, r, hit) {
    const svg = document.getElementById("svg-chart");
    const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");

    point.setAttribute("cx", x / r*120.0 + 150);
    point.setAttribute("cy", 150 - y / r*120.0);
    point.setAttribute("r", "2");
    point.setAttribute("class", hit ? "point-hit" : "point-miss");

    svg.appendChild(point);
}

function updateTable() {
    document.getElementById("main-form:update-table").click();
}


document.addEventListener('DOMContentLoaded', function() {
    redrawChart();

    const svgChart = document.getElementById('svg-chart');
    if (svgChart) {
        svgChart.addEventListener('click', function(e) {

            const rValue = getSelectedR();
            if (!rValue) {
                showFormError('Выберите значение R, чтобы кликать по графику');
                return;
            }
            const r = parseFloat(rValue);

            clearFormError();

            const clientX = e.clientX;
            const clientY = e.clientY;
        
            const point = new DOMPoint(clientX, clientY);
        
            const ctm = svgChart.getScreenCTM();
        
            const svgPoint = point.matrixTransform(ctm.inverse());

            const x = (svgPoint.x - 150) / 120 * r;
            const y = (150 - svgPoint.y) / 120 * r;

            fetch('newPoint', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `x_value=${x}&y_value=${y}&r_value=${r}`,
            })
            .then(response => response.json())
            .then(data => {
                if (data.hit !== undefined) {
                    showPoint(x, y, r, data.hit);
                    updateTable();
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });  
    }

    const radios = document.getElementsByName('main-form:r');
    
    for (let i = 0; i < radios.length; i++) {
        radios[i].addEventListener('change', function() {
            redrawChart();
        });
    }    

});
