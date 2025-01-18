<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Visitor Form</title>
    </head>
    <body>
        <form action="securityController" method="POST" enctype="multipart/form-data">
            <p>Visitor's Name</p>
                <input type="text"  name ="visitorname"/><br>
            <p>IC/Passport</p>
                <input type="text" name="icpassport"/><br>
            <p>Plate Number</p>
                <input type="text" name="plateno"/><br>
            <p>Entry Time</p>
                <input type="time" name="entrytime"/><br>
            <p>Date of Visit</p>
                <input type="date" name="datevisit"/><br>
            <p>Purpose of Visit</p>
                <input type="text" name="purposevisit"/><br>
            <p>Phone Number</p>
                <input type="text" name="phoneno"/><br>
            <button type="submit" value="Submit" >Submit</button>
            <button type="reset" >Cancel</button>
            <input type="hidden" name="accessType" value="addVisitor">
            <input type="hidden" name="userid" value="10001">
        </form>
    </body>
</html>
