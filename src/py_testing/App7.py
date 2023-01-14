factor = True
switch = 0

for i in range(20):

    if(factor):
        switch = 0
    else:
        switch = 4

    factor = not factor

    print("a.add( new Position(", switch  ,", ", i*2,", false));")


for i in range(20):

    if(factor):
        switch = 0
    else:
        switch = 4

    factor = not factor

    print("a.add( new Position(", i*2  ,", ", switch,", false));")