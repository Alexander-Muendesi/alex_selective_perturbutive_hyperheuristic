# Data set description
## COURSES:
c0001 t000 6 4 130

c0001: course code
t000: teacher code
6: duration or number of periods required to complete the course
4: The number of lectures that need to be scheduled
130: the number of students enrolled in the course

## ROOMS:
B	200

B: room code
200: max number of students that can be accomodated

## CURRICULA:
q000  4 c0001 c0002 c0004 c0005

q000: curriculum code
4: number of courses that are present in the curriculum
*: remaining columns indicate the courses in the curriculum

## UNAVAILABILITY_CONSTRAINTS:
c0001 4 0

column 1: the course code
column 2: the number of periods that the course is unavailable for
column 3: the specific period/time slot during which the course is unavailable.