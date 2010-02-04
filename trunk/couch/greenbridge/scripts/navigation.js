$(function() {
	 $.get("_header.html", function(resp) {
		$("#header").append(resp);
		$('#tagsearch').autocomplete('/conversations/_design/tags/_view/by_tag_grouped', {
			multiple: false,
			minChars: 1,
			max: 300,
			dataType: "json",
			extraParams: {
				startkey: function() {return '"' + $('#tagsearch').val() + '"'},
				group: 'true'
			},
			parse: function(data) {
				var arr = new Array();
				for (var i=0; i < data.rows.length;i++) {
					arr[i] = {
						data: data.rows[i],
						value: data.rows[i].key,
						result: data.rows[i].key
					};
				}
				return arr;
			},
			formatItem: function(item) {
				return item.key + " (" + item.value + ")" ;
			}

		});
	 });
	 $('#content').corner();
});

function findDBName() {
	var match = /\/_(\w+)\//.exec(window.location);
    var db_name = match[1];
	return db_name;
}

