import csv
import matplotlib.pyplot as plt

with open("results/dp.csv") as f:
    dp = list(csv.DictReader(f))

x = [int(a["size"]) for a in dp]
y = [float(a["lcs"]) for a in dp]
z = [float(a["edit"]) for a in dp]

plt.plot(x, y, marker="o", label="LCS")
plt.plot(x, z, marker="o", label="Edit Distance")
plt.xlabel("String length")
plt.ylabel("Average time (ms)")
plt.title("Dynamic Programming")
plt.legend()
plt.grid()
plt.tight_layout()
plt.savefig("results/dp.png")
plt.close()

with open("results/match.csv") as f:
    data = list(csv.DictReader(f))

for name in ["Naive", "KMP", "RK"]:
    rows = [a for a in data if a["algorithm"] == name and a["case"] == "present"]
    x = [int(a["size"]) for a in rows]
    y = [float(a["ms"]) for a in rows]
    plt.plot(x, y, marker="o", label=name)

plt.xlabel("Text length")
plt.ylabel("Average time (ms)")
plt.title("String Matching - Present Pattern")
plt.legend()
plt.grid()
plt.tight_layout()
plt.savefig("results/match.png")
plt.close()
