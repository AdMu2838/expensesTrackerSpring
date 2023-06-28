import {updateTables} from "../jsModules/dashboardFunctions.js"

// @ts-check
//Variables
const btnRegIngreso = document.querySelector("label#addRegForm_btnRegTypeIngreso");
const btnRegEgreso = document.querySelector("label#addRegForm_btnRegTypeEgreso");
const selectCatIngreso = document.querySelector("select#selectCategoriasIngresos");
const selectCatEgreso = document.querySelector("select#selectCategoriasEgresos");

//Eventos
//Ajustando eventos a los btns

btnRegIngreso.addEventListener("click", addRegFormBtnTypeClick);
btnRegEgreso.addEventListener("click", addRegFormBtnTypeClick);

//Mandar datos al servidor
document.getElementById("btnAgregarForm").addEventListener("click", enviarRegistro);


/**
@param {Event & { target: HTMLElement }} e
 */
function addRegFormBtnTypeClick(e){
	
	let elem = e.target;
	elem.classList.remove("btn-light","border-dark")
	if(elem === btnRegIngreso){
		elem.classList.add("btn-success");
		
		selectCatIngreso.removeAttribute("disabled");
		selectCatIngreso.removeAttribute("hidden");
		
		selectCatEgreso.setAttribute("disabled", "disabled");
		selectCatEgreso.setAttribute("hidden", "true");
		
		btnRegEgreso.classList.remove("btn-danger");
		btnRegEgreso.classList.add("btn-light", "border-dark");
	}
	else{
		elem.classList.add("btn-danger");
		
		selectCatEgreso.removeAttribute("disabled");
		selectCatEgreso.removeAttribute("hidden");
		
		selectCatIngreso.setAttribute("disabled", "disabled");
		selectCatIngreso.setAttribute("hidden", "true");
		
		btnRegIngreso.classList.remove("btn-success");
		btnRegIngreso.classList.add("btn-light", "border-dark");
		
	}
}



function enviarRegistro(){
	
	if (!validateAddForm()) return;
	
	// @ts-expect-error
	const data = new URLSearchParams( new FormData(document.forms["addRegForm"])).toString();
	
	
	fetch("/dashboard/agregar_registro",{
		
		method: 'POST',
		credentials: "include",
		headers: {'Content-Type': "application/x-www-form-urlencoded"},
		body: data
		
	})
	.then((res)=>{
		
		if(res.status != 200) {alert("Error al agregar")}
		return res.text();
		
	})
	.then((data)=>{
		
		if(Number.parseInt(data) == 1) {
		alert("Registro agregado con éxito");
		updateTables();
		
		return;
		}
		alert("No se pudo agregar el registro :c");
		
		
	})
	.catch((err)=>{console.log(err)})
	
	document.forms["addRegForm"].reset();
	
}

function validateAddForm(){
	
	document.getElementsByClassName("dangerMsgAgregarFrm")[0].setAttribute("hidden","true");
	document.getElementsByClassName("dangerMsgAgregarFrm")[1].setAttribute("hidden","true");
	
	let desc = document.forms["addRegForm"]["reg_descripcion"].value;
	if (String(desc).trim().length == 0){
		document.getElementsByClassName("dangerMsgAgregarFrm")[0].removeAttribute("hidden");
		return false;
	}
	
	let impac = document.forms["addRegForm"]["reg_impacto"].value;
	if (Math.sign(impac)==0 || String(impac).match(/^\d+(.\d{1,2})?$/) == null){
		document.getElementsByClassName("dangerMsgAgregarFrm")[1].removeAttribute("hidden");
		return false;
	}
	return true;
}



