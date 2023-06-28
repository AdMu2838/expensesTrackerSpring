// @ts-nocheck

import {updateTables} from '../jsModules/dashboardFunctions.js';


//Variables
// var makes variables global to all the scripts
// let and const don't
//functions are also global

let prvSelectedNav = document.getElementById("defaultSelectedNav");
let arrayOptions = document.getElementsByClassName("nav-link");
let prvShowedView = document.getElementById("defaultShowedView");

let arrayBtnTableActionDelete = document.getElementsByClassName("btnTableActionDelete");
let arrayBtnTableActionEdit = document.getElementsByClassName("btnTableActionEdit");


/**
@param {Event & {target: HTMLElement}} e
 */
const tableEditModalBtnSaveClick = (e)=>{
	
	
	if(!validateEditForm()) return;
	// @ts-expect-error
	const data = new URLSearchParams( new FormData(document.forms["tableEditModalForm"])).toString();
	
	fetch("/dashboard/actualizar_registro", {
		
		method: 'POST',
		credentials: 'include',
		headers: {'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8"},
		body: data
		
	})
	.then(res=>{
		if(res.status != 200){alert("Error al agregar")}
		
		return res.text();
		
	})
	.then(data=>{
		
		if(Number.parseInt(data) == 1) {
		alert("Registro actualizado con éxito");
		updateTables();
		return;
		}
		alert("No se pudo actualizar el registro :c");
		
	})
	.catch(err=>{
		console.log(err);
	})
	//@ts-expect-error
	bootstrap.Modal.getInstance(document.querySelector("#tableEditModal")).hide();
	
}

const validateEditForm = ()=>{
	
	document.getElementsByClassName("tableEditModalForm_dangerMsgEditFrm")[0].setAttribute("hidden","true");
	document.getElementsByClassName("tableEditModalForm_dangerMsgEditFrm")[1].setAttribute("hidden","true");
	
	let desc = document.forms["tableEditModalForm"]["reg_descripcion"].value;
	if (String(desc).trim().length == 0){
		document.getElementsByClassName("tableEditModalForm_dangerMsgEditFrm")[0].removeAttribute("hidden");
		return false;
	}
	
	let impac = document.forms["tableEditModalForm"]["reg_impacto"].value;
	if (Math.sign(impac)==0 || String(impac).match(/^\d+(.\d{1,2})?$/) == null){
		document.getElementsByClassName("tableEditModalForm_dangerMsgEditFrm")[1].removeAttribute("hidden");
		return false;
	}
	return true;
}

/**
@param {Event & {target: HTMLElement}} e
 */
 //@ts-expect-error
window.btnTableActionEditClick = (e)=>{

	let selectCatEgreso = document.getElementById("tableEditModalForm_selectCatEgreso");
	let selectCatIngreso = document.getElementById("tableEditModalForm_selectCatIngreso");
	
	let editForm = document.forms["tableEditModalForm"];
	editForm["reg_tipo"].value = (Math.sign(Number.parseFloat(e.target.dataset.regimpact)) != -1) ? "ingreso" : "egreso";
	editForm["reg_descripcion"].value = e.target.dataset.regdesc;
	editForm["reg_id"].value = e.target.dataset.regid;
	editForm["reg_impacto"].value = Math.abs(Number.parseFloat(e.target.dataset.regimpact));
	
	//Identificamos si se trata de un impacto de ingreso o egreso
	if (Math.sign(Number.parseFloat(e.target.dataset.regimpact)) == -1){ //Egreso
		
		selectCatEgreso.querySelectorAll("options").forEach((option)=>{
			option.removeAttribute("selected")
		});
		
		selectCatEgreso.removeAttribute("hidden");
		selectCatEgreso.removeAttribute("disabled");
		
		selectCatIngreso.setAttribute("hidden", "true");
		selectCatIngreso.setAttribute("disabled", "disabled");
		
		selectCatEgreso.querySelector(`option[value='${e.target.dataset.regidcat}']`).setAttribute("selected","selected");
		
	}
	else{ //Ingreso
	
		selectCatIngreso.querySelectorAll("options").forEach((option)=>{
			option.removeAttribute("selected")
		});
	
		selectCatEgreso.setAttribute("hidden", "true");
		selectCatEgreso.setAttribute("disabled", "disabled");
		
		selectCatIngreso.removeAttribute("hidden");
		selectCatIngreso.removeAttribute("disabled");
		
		selectCatIngreso.querySelector(`option[value='${e.target.dataset.regidcat}']`).setAttribute("selected","selected");
		
	}
	
}



/**

@param {Event & {target: HTMLElement}} e

 */
//@ts-expect-error
window.btnTableActionDeleteClick = (e)=>{
	
	if (confirm(`¿Seguro que desea eliminar el registro ${e.target.dataset.regid}?`)){

		fetch("/dashboard/eliminar_registro",{
			
			method: 'POST',
			credentials: 'include',
			headers: {'Content-Type': "application/x-www-form-urlencoded; charset=UTF-8"},
			body: `reg_id=${e.target.dataset.regid}`
			
		})
		.then((res)=>{
			if(res.status != 200) {alert("Error al eliminar")}
			return res.text();
		})
		.then((data)=>{
			if (Number.parseInt(data) == 1){
				updateTables();
			}
		})
		.catch((err)=>{
			console.log(err)
		});
	}
	
}

/**
@param {Event & { target: HTMLElement }} e
 */

const navClick = (e)=>{
	
	//Ocultamos la view actual mostrada
	prvShowedView.setAttribute("hidden","true");
	//Asignamos a la var la view seleccionada
	prvShowedView = document.querySelector(`div[data-view='${e.target.dataset.view}']`);
	//Mostramos la view seleccionada
	prvShowedView.removeAttribute("hidden");
	
	//Aplica el estilo correspondiente al nav seleccionado y
	//remueve el del anterior
	prvSelectedNav.classList.remove("active");
	prvSelectedNav.classList.add("link-dark");
	
	e.target.classList.add("active");
	e.target.classList.remove("link-dark");
	prvSelectedNav = e.target;
}


//Ajustando Eventos a varios elementos

for (let btnTableActionDelete of arrayBtnTableActionDelete){
	btnTableActionDelete.addEventListener("click" , window.btnTableActionDeleteClick);
}

for (let btnTableActionEdit of arrayBtnTableActionEdit){
	btnTableActionEdit.addEventListener("click", window.btnTableActionEditClick);
}

for (let nav of arrayOptions){
	nav.addEventListener("click",navClick)
}

// Ajustando eventos a elementos individuales

document.getElementById("tableEditModal_btnSave").addEventListener("click", tableEditModalBtnSaveClick);





