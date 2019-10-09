import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['totalTime'] = data['parameterizationCombinations'] * data['executionTimeDefault'] / 60

grouped = data.groupby('project')

array = []
for project, benchmarks in grouped:
    print(project + " -> " + str(benchmarks['totalTime'].sum()))
    array.append(benchmarks['totalTime'].sum()/60)

print("max: " + str(np.max(array)))
print("median: " + str(np.median(array)))