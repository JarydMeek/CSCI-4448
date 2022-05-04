from sqlalchemy import create_engine
import sys

engine = create_engine('postgresql://metnhjqpqtvokb:1a1def84baf0dc727334dcdb27b06fad1c2681e5c1790bb247b035a705065e83@ec2-3-223-213-207.compute-1.amazonaws.com:5432/ddv0rcqrgn9g8h', echo = False)

def showResults(res):
    f = open("tempo.txt", "w")
    for row in res:
        print(row)
        f.write(str(row))
    f.close()


for i, arg in enumerate(sys.argv):
    if (arg == "--all"):
        #res = engine.execute("SELECT json_agg(plantbase) FROM plantbase;")
        res = engine.execute("SELECT row_to_json(plants) FROM (SELECT * FROM plantbase RIGHT JOIN planttype ON planttype.type = plantbase.type) as plants;")
        showResults(res)
    elif (arg == "--get"):
        target = sys.argv[i + 1]
        res = engine.execute(f"SELECT row_to_json(x) FROM (SELECT * FROM plantbase RIGHT JOIN planttype ON planttype.type = plantbase.type WHERE comname = '{target}') AS x;")
        showResults(res)
