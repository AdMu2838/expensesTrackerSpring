// @ts-check
// Funciones
//code a way to optimize the updating

const spanPreActual =  document.querySelector("#chartPresupuesto_preActual");

let presupuesto = 0;

fetch("DashboardServlet?accion=queryPresupuesto",{
	method: 'GET',
})
.then(res=>
	res.text()
)
.then(data=>{
	presupuesto = Number.parseFloat(data);
})

// @ts-expect-error
const chartEgresos = new Chart(
	document.getElementById('chartEgresos'),
    {
	    type: 'line',
	    data: {
		    labels: [],
		    datasets: [{
				backgroundColor: 'rgb(255, 99, 132)',
				borderColor: 'rgb(255, 99, 132)',
				data: [],
	    	}]
  		},
	     options: {
			responsive: true, 
			maintainAspectRatio: false,
			plugins: {
	            title: {
	                display: true,
	                text: 'Evolución de los Egresos'
	        	},
	        	legend: {
					display: false
				}
        	},
        	scales: {
				x:{
					title: {
						display: true,
						text: "Días del mes"
					}
				},
				y:{
					title:{
						display: true,
						text: "Egreso registrado (S/.)"
					}
				}
			}
		}
	}
  );

// @ts-expect-error
const chartIngresos = new Chart(
	document.getElementById('chartIngresos'),
    {
	    type: 'line',
	    data: {
		    labels: [],
		    datasets: [{
				backgroundColor: 'rgb(38,208,124)',
				borderColor: 'rgb(38,208,124)',
				data: [],
	    	}]
  		},
	     options: {
			responsive: true, 
			maintainAspectRatio: false,
			plugins: {
	            title: {
	                display: true,
	                text: 'Evolución de los Ingresos'
	        	},
	        	legend: {
					display: false
				}
        	},
        	scales: {
				x:{
					title: {
						display: true,
						text: "Días del mes"
					}
				},
				y:{
					title:{
						display: true,
						text: "Ingreso registrado (S/.)"
					}
				}
			}
		}
	}
  );

// @ts-expect-error
const chartPresupuesto = new Chart(
	document.getElementById('chartPresupuesto'),
    {
	    type: 'line',
	    data: {
		    labels: [],
		    datasets: [{
				backgroundColor: 'rgb(46,103,248)',
				borderColor: 'rgb(46,103,248)',
				data: [],
	    	}]
  		},
	    options: {
			responsive: true, 
			maintainAspectRatio: false,
			plugins: {
	            title: {
	                display: true,
	                text: 'Evolución del Presupuesto'
	        	},
	        	legend: {
					display: false
				}
        	},
        	scales: {
				x:{
					title: {
						display: true,
						text: "Días del mes"
					}
				},
				y:{
					title:{
						display: true,
						text: "Presupuesto (S/.)"
					}
				}
			}
		}
	}
  );
  

const updateCharts = async ()=>{
	
	/** @type {Array<Array>} */
	let arrayData = await queryGraphicData()

	let arrayDataGeneral = arrayData[0];
	let arrayDataIngresos = arrayData[1];
	let arrayDataEgresos = arrayData[2];
	
	



	//Updating chart Egresos
	
	let labels = arrayDataEgresos.map((elem)=>elem.fecha)
	let values = arrayDataEgresos.map((elem)=>Math.abs(elem.impacto));
	
	chartEgresos.data.labels = labels;
	chartEgresos.data.datasets[0].data = values;

	chartEgresos.update();

	//Updating chart Ingresos
	
	labels = arrayDataIngresos.map((elem)=>elem.fecha)
	values = arrayDataIngresos.map((elem)=>Math.abs(elem.impacto));
	
	chartIngresos.data.labels = labels;
	chartIngresos.data.datasets[0].data = values;

	chartIngresos.update();
	
	
	//Updating chart Presupuesto

	let temp_pre = presupuesto;
	
	
	
	arrayDataGeneral = arrayDataGeneral.map(elem=>{
		temp_pre += elem.impacto;
		return {
			fecha: elem.fecha,
			presupuesto: temp_pre
		}
		});
	
	spanPreActual.textContent = "Presupuesto Actual: "+temp_pre;
	
	labels = arrayDataGeneral.map((elem)=>elem.fecha)
	values = arrayDataGeneral.map((elem)=>elem.presupuesto);
	
	chartPresupuesto.data.labels = labels;
	chartPresupuesto.data.datasets[0].data = values;

	chartPresupuesto.update();
	
}

//The graphic works with impact and date of the logs
const queryGraphicData = async () => {
	
	return fetch("DashboardServlet?accion=queryImpactoYFecha", {
		method: 'GET',
		credentials: 'include',
		headers: {'Content-Type': "application/x-www-form-urlencoded"}
	})
	.then(res=>{
		if (res.status != 200) alert("Proceso 'queryGraphicData' no completado")
		return res.json();
	})
	.catch(console.log);
}


const navBtn = document.querySelector(".nav-link[data-view='view_estadisticas']");
navBtn.addEventListener("click", updateCharts);



