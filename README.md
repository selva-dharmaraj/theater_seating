Theater Seating Arrangement:
===========================


Code language: Java8
Framework: Spring boot, Spring batch
Build tool: Maven

How to run the program:

1. Download the jar file. (theater.seating-0.0.1-SNAPSHOT.jar)
https://github.com/selva-dharmaraj/theater_seating/blob/master/theater.seating-0.0.1-SNAPSHOT.jar

2. Download input files. (theater_layout.txt, theater_seating_request.txt)
https://github.com/selva-dharmaraj/theater_seating/blob/master/theater_layout.txt
https://github.com/selva-dharmaraj/theater_seating/blob/master/theater_seating_request.txt

3. Keep both files is same directory.

4. Execute below command: (Please make sure you have JRE8 available in your computer)

Usage: java -jar <jar_file> <layout_file> <request_file>"

Example: java -jar target/theater.seating-0.0.1-SNAPSHOT.jar theater_layout.txt theater_seating_request.txt

(Or)

Example: java -jar theater.seating-0.0.1-SNAPSHOT.jar /Users/selva/theater_layout.txt /Users/selva/theater_seating_request.txt
               
