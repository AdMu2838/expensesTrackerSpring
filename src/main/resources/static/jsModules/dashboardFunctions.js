// @ts-nocheck

/**
	@typedef Registro
	@type {object}
	@property {string} id
	@property {string} descripcion
	@property {number} impacto
	@property {string} categoria
	@property {string} fecha
	@property {number} id_categoria
*/

async function updateTables(){
	
	/**@type {Registro[]} */
	const dataRegEgresos = await queryRegEgresos();
	/**@type {Registro[]} */
	const dataRegIngresos = await queryRegIngresos();
		
	dataRegEgresos.sort((a,b) => 
		Number.parseInt(a.fecha.split("-")[2]) > Number.parseInt(b.fecha.split("-")[2]) ? 1 : -1
	);
	
	dataRegIngresos.sort((a,b) => 
		Number.parseInt(a.fecha.split("-")[2]) > Number.parseInt(b.fecha.split("-")[2]) ? 1 : -1
	);
	
	
	/**@type {HTMLTableSectionElement} */
	const tablaIngresosBody = document.querySelector("#tablaIngresosBody");
	/**@type {HTMLTableSectionElement} */
	const tablaEgresosBody = document.querySelector("#tablaEgresosBody");
	
	while(tablaIngresosBody.rows.length > 0){tablaIngresosBody.deleteRow(0);}
	
	while(tablaEgresosBody.rows.length > 0){tablaEgresosBody.deleteRow(0);}
	
	for (let data of dataRegIngresos){
		
		tablaIngresosBody.insertRow().innerHTML=
`<th scope="row">${data.id}</th><td>${data.descripcion}</td>
<td>${data.impacto}</td>
<td>${data.categoria}</td>
<td>${data.fecha}</td>
<td align="center"><img onClick="window.btnTableActionEditClick(event)" data-regid="${data.id}" data-regdesc="${data.descripcion}" data-regimpact="${data.impacto}" data-regidcat="${data.id_categoria}" role="button" src="img/editar.png" title="Editar" class="btnTableActionEdit btn btn-light" data-bs-toggle="modal" data-bs-target="#tableEditModal"></td>
<td align="center"><img onClick="window.btnTableActionDeleteClick(event)" role="button" src="img/eliminar.png" title="Eliminar" class="btnTableActionDelete btn btn-light" data-regid="${data.id}"></td>`	
	}
	
	for (let data of dataRegEgresos){
		
		tablaEgresosBody.insertRow().innerHTML=
`<th scope="row">${data.id}</th><td>${data.descripcion}</td>
<td>${data.impacto}</td>
<td>${data.categoria}</td>
<td>${data.fecha}</td>
<td align="center"><img onClick="btnTableActionEditClick(event)" data-regid="${data.id}" data-regdesc="${data.descripcion}" data-regimpact="${data.impacto}" data-regidcat="${data.id_categoria}" role="button" src="img/editar.png" title="Editar" class="btnTableActionEdit btn btn-light" data-bs-toggle="modal" data-bs-target="#tableEditModal"></td>
<td align="center"><img onClick="btnTableActionDeleteClick(event)" role="button" src="img/eliminar.png" title="Eliminar" class="btnTableActionDelete btn btn-light" data-regid="${data.id}"></td>`	
	}
		
};

async function queryRegEgresos(){
	
	return fetch("/dashboard/queryRegEgresos", {
		method:'GET',
		credentials: 'include'
	})
	.then((res)=>{
		if (res.status != 200) alert("Error al solicitar egresos");
		return res.json();
	})
	
};

async function queryRegIngresos(){
	
	return fetch("/dashboard/queryRegIngresos", {
		method:'GET',
		credentials: 'include'
	})
	.then((res)=>{
		if (res.status != 200) alert("Error al solicitar ingresos");
		return res.json();
	})
	
};

export {updateTables}