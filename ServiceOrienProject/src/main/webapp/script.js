/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready( function () {
    $('#myTable').DataTable();
    getData();  
} );
var timestamp;
function latestTimetamp(data){
    var datesArray = []
    data.forEach(function(time){
        datesArray.push(time.time)
    })
    var sortedDates = datesArray.sort(function (var1, var2) { 
   var a= new Date(var1), b = new Date(var2);
    if (a < b)
      return 1;
    if (a > b)
      return -1;
   
    return 0;
});

var newestTimestamp = sortedDates[0].concat(GMT)


var time = sortedDates[0].split(" ")
var date = new Date(time[0])
const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
var newDate = date.toLocaleDateString(undefined, options);

var unsortedDate = newDate.split(" ");
var day = unsortedDate[0].substring(0, 3);
var month = unsortedDate[1].substring(0,3);
var num = unsortedDate[2].substring(0,2);

var fullDate = day.concat(",", " ", num, " ", month, " ", unsortedDate[3])
var GMT = "GMT"
var time = time[1].concat(" ")
var adjustTime = parseInt(time.substring(0,2)) -1;
var adjustedTime = adjustTime.toString();
var newTime = adjustedTime.concat(time.substring(2,8));
var IfModified = fullDate.concat(" ", newTime, " ",GMT);
var testDate = IfModified.toString();
$('#Time').text(testDate)
window.timestamp =  testDate;

}
const getData = () =>{
    fetch('webresources/people', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    })
            .then( (response) => {
                console.log(response)
        if(response.ok){
            
            return response.json();
        }
        else{
            console.log("Error getting the data");
        }
    })
            .then( (json) => {
                if(json){
                    console.log(json);
                    renderList(json)
                    latestTimetamp(json)
                    
                }
                else{
                    console.log("No data");
                }
    })
            .catch(error =>{
                console.log("Catch: " + error);
    });
};
var tableData;
function renderList(data){
    tableData = data;

    $('#table_body tr').remove();
    $.each(data, function(data, people){
        $('#table_body').append('<tr><td><a href="#crud" id="' + people.id + '">'+people.id+'</a></td><td>'+people.firstName+'</td><td>'+people.lastName+'</td><td>'+people.car+'</td><td>'+people.country+'</td><td>'+people.city+'</td><td>'+people.time+'</td>');
    });

   table = $('#table_id').DataTable({
        paging: true,
        searching: true,
        "pageLength": 10
    });
}

$(document).on("click", "#postButton",function(){
     alert("Hello")
   
});
function addPerson(){
    alert("pressed");
    var firstName = document.getElementById("fname").value;
    var lastName = document.getElementById("lname").value;
    var car = document.getElementById("car").value;
    var city = document.getElementById("city").value;
    var country = document.getElementById("country").value;
    
    $.ajax({
   url: "http://localhost:8080/ServiceOrienProject/webresources/people",
   type: "POST",
   data: JSON.stringify({
            'firstName': firstName,
            'lastName': lastName,
            'car': car,
            'city': city,
            'country': country
         }),
   contentType: 'application/json; charset=utf-8',
   success: function(){
       table.destroy();
       getData()
       $('#Status2').text('200 OK');
       $('#Time').text(timestamp); 
   }
   
   
});
}
function updatePerson(){
    alert("pressed");
    var id = document.getElementById("id").value
    var firstName = document.getElementById("fname").value;
    var lastName = document.getElementById("lname").value;
    var car = document.getElementById("car").value;
    var city = document.getElementById("city").value;
    var country = document.getElementById("country").value;
    
    $.ajax({
   url: "http://localhost:8080/ServiceOrienProject/webresources/people",
   type: "PUT",
   data: JSON.stringify({
            'id': id,
            'firstName': firstName,
            'lastName': lastName,
            'car': car,
            'city': city,
            'country': country
         }),
   contentType: 'application/json; charset=utf-8',
   success: function(){
       table.destroy();
       getData()
       $('#Status2').text('200 OK');
       $('#Time').text(timestamp); 
   }
   
});
}

function refreshPeople(){
    console.log(timestamp)
    console.log('Wed, 21 Apr 2021 11:47:16 GMT')
    fetch('webresources/people/refresh', {
        method: 'GET',
        headers: {
            'If-Modified-Since':  timestamp,
            'Accept': 'application/json'
        }
    })
            .then( (response) => {
        console.log(response)
        if(response.not_modifed){
            console.log("304")
        }
        
        if(response.ok){
            $('#Status').text('200 OK');
            return response.json();
        }
        else{
            console.log("Error getting the data");
        }
    })
            .then( (json) => {
                if(json){
            table.destroy()
                    renderList(json)
                    latestTimetamp(json)
                    $('#Status').text('200 OK');
                    $('#Time').text(timestamp); 
                }
                else{
                    $('#Status').text('304 Not Modified');
                    $('#Time').text(timestamp); 
                }
    })
            .catch(error =>{
                console.log("Catch: " + error);
    });
}
    
