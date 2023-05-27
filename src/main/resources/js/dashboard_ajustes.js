/**
 * 
 */
 // @ts-check
 const sendSettingsForm = (e)=>{

	//AÑADIR VALIDACIONES
	
	//@ts-expect-error
	const data = new URLSearchParams(new FormData(document.forms["editSettingsForm"])).toString();
	
	fetch("DashboardServlet?accion=actualizarUsuario",{
		method: 'POST',
		headers : {'Content-Type':"application/x-www-form-urlencoded"},
		body : data
	})
	.then(res=>{
		
		if (res.status != 200) alert("La petición no fue procesada")
		return res.text();
		
	})
	.then(alert)
	.catch(console.log);
}

const downloadReg = async ()=>{
	
	/**@type {Array<Object>}*/
	let registros = await fetch("DashboardServlet?accion=queryRegsParaExportar")
	.then(res=>res.json())
	.catch(console.log)
	
	let data = [];
	
	data.push("id,descripcion,categoria,impacto,fecha")
	registros.forEach((obj)=>{
		//@ts-expect-error
		data.push(Object.values(obj).join(","));
	});
	
	
	 const blob = new Blob([data.join("\n")], { type: 'text/csv' });
	 
	 const url = window.URL.createObjectURL(blob)
 
    const a = document.createElement('a')
 
    a.setAttribute('href', url)
    a.setAttribute('download', 'registros.csv');
    a.click()
	
}


document.querySelector("#editSettingsForm_btnSend").addEventListener("click", sendSettingsForm);

document.querySelector("#exportRegBtn").addEventListener("click", downloadReg);

 