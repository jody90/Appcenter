jQuery(document).ready(function($) {

	function getFormData(callback) {
		var data = $("#form_content_json").attr("value");
		callback(data);
	}
	
	var formBuilder;
	
	getFormData(function(data) {
		var formRenderOpts = {
			render: true,			
			formData : data,
			stickyControls: true,
			showActionButtons: false,
			dataType: 'json',
//		    typeUserAttrs: {
//		    	paragraph: {
//		    		className: {
//		    			label: 'Class',
//		    			options: {
//		    				'margin-bottom-sm': 'Abstand unten klein',
//		    				'margin-bottom-md': 'Abstand unten mittel',
//		    				'margin-bottom-lg': 'Abstand unten gross',
//		    			},
//		    		}
//		    	}
//		    }
		};
			
		var formBuilderEditor = $("#fb-editor");
		var form = formBuilderEditor.formBuilder(formRenderOpts);
		formBuilder = form.data('formBuilder');
	});
	

	$(".save-form").click(function(e) {
//		e.preventDefault();
		var formDataObject = JSON.parse(formBuilder.formData);
		var json = JSON.stringify(formDataObject);
		
		var formRenderOpts = {
			render: false,
			formData: json,
			dataType: 'json',
		};
		
		// Grab markup and escape it
		var markup = new FormRenderFn(formRenderOpts).markup;
		
		$("#form_content_html").html(markup);
		$("#form_content_json").val(json);
		
	});
});