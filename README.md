# house-simulator
The aim of this project is to implement a Java program for simulating the distribution of student houses by using Java Collections Framework.


# Details (Prepared by Ozlem Simsek)
The University of Datalonya offers houses for the students. All houses
are for one student only. But they can be in various conditions: old, new,
needs renovation, broken oven, broken shower, new kitchen, new bed etc.
According to these conditions each house has a rating point between 0 and
10. Students study at the university up to 8 semesters. If they are located
at a house, they stay until graduation. But each student has his/her own
criterion on house selection. This criterion is a threshold on the house’s
rating. For example, if the student wants a house with minimum rating 4,
then he/she is not located at houses with rating 2 or 3 (any rating below 4)
even if they are the only available houses. New allocations are made at the 
beginning of each semester. House and student lists are checked for new 
allocations if possible. Now you are enrolled at the University of Datalonya 
Dormitory Office. The current list of houses and students are transferred to 
you. From now on, you are responsible for the allocation of the houses.

You are supposed to arrange the lists of houses and students in collec-
tions of your choice and simulate the allocations until all students in the list
graduate. In your simulation, check for matching houses and students at
every new semester. The output of your simulation is the list of students
who cannot stay at any house.

Input format:<br/>
For houses:   h id duration rating<br/>
For students: s id name duration rating<br/>

Output format: (student names who couldn't stay at hose in ascending order of IDs)<br/>
name<br/>

Example:<br/>

Input:<br/>
h 1 0 8<br/>
s 10 Ali 3 9<br/>
h 2 0 4<br/>
h 3 0 7<br/>
s 11 Melis 3 9<br/>
s 12 Ayse 5 4<br/>
h 4 6 9<br/>
s 13 Selim 5 3
h 5 5 5<br/>

Output:<br/>
Ali<br/>
Melis
