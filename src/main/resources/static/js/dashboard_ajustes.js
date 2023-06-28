/**
 * 
 */
 // @ts-check
 const sendSettingsForm = (e)=>{

	//AÑADIR VALIDACIONES
	
	//@ts-expect-error
	const data = new URLSearchParams(new FormData(document.forms["editSettingsForm"])).toString();
	
	fetch("/dashboard/actualizar_usuario",{
		method: 'POST',
		headers : {'Content-Type':"application/x-www-form-urlencoded"},
		body : data
	})
	.then(res=>{
		
		if (res.status != 200) alert("La petición no fue procesada")
		return res.text();
		
	})
	.then((data)=>{
			if (Number.parseInt(data) == 0){
				alert("Error al actualizar")
			}else{
				window.location.href="/logout";
			}
		})
	.catch(console.log);
}

const downloadReg = async ()=>{
	
	/**@type {Array<Object>}*/
	let registros = await fetch("/dashboard/export_logs_csv")
	.then(res=>res.json())
	.catch(console.log)
	
	let data = [];
	
	data.push("id,descripcion,categoria,impacto,fecha")
	registros.forEach((obj)=>
		{

		data.push(Object.values(obj).join(","));
	});
	
	
	 const blob = new Blob([data.join("\n")], { type: 'text/csv' });
	 
	 const url = window.URL.createObjectURL(blob)
 
    const a = document.createElement('a')
 
    a.setAttribute('href', url)
    a.setAttribute('download', 'registros.csv');
    a.click()
	
}


// @ts-ignore
document.querySelector("#editSettingsForm_btnSend").addEventListener("click", sendSettingsForm);

// @ts-ignore
document.querySelector("#exportRegBtn").addEventListener("click", downloadReg);

 