import itertools
from collections import defaultdict
import statistics as stat
from pprint import pprint
from typing import DefaultDict, List, Tuple

import matplotlib.pyplot as plt
import numpy as np
import csv


statistics: DefaultDict[int, DefaultDict[int, List[Tuple[int, int]]]] = defaultdict(lambda: defaultdict(list))
column_names = ["size", "degree", "max-matching-size", "time"]
for number in range(3, 11):
    filename = f"data/max-matching{number}.csv"
    with open(filename) as file:
        reader = csv.DictReader(file, column_names, delimiter=";")
        for line in reader:
            statistics[number][int(line["degree"])].append((line["max-matching-size"], line["time"]))

# # max matching (per k)
# for k in range(3, 11):
#     rng = np.arange(1, k+1)
#     tmp = list()
#     for i in statistics[k].keys():
#         tmp.append(np.mean([int(a[0]) for a in statistics[k][i]]))
#     plt.plot(rng, tmp)
#     plt.title(f"max matching size for k = ${k}")
#     plt.xlabel("degree")
#     plt.ylabel("max matching size")
#     plt.savefig(f"max-matching{k}.png")
#     plt.show()

# time (per i)
for i in range(1, 11):
    rng = np.arange(max(3, i), 11)
    tmp = list()
    for k in range(max(3, i), 11):
        tmp.append(np.mean([int(a[1]) for a in statistics[k][i]]))
    plt.plot(rng, tmp, marker="o")
    plt.title(f"time for i = ${i}")
    plt.xlabel("size")
    plt.ylabel("time in nanoseconds")
    plt.savefig(f"max-matching-for-i-{i}.png")
    plt.show()