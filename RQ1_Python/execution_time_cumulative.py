import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['executionTimePercentage'] != 1]
x = filtered['executionTimePercentage']

values, base = np.histogram(x, bins=1000, range=[0,1], weights=np.ones(len(x)) / len(x))
cumulative = np.cumsum(values)
plt.plot(base[:-1], cumulative)
plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('executionTimePercentage')
plt.ylabel('cumulativePercentage')
plt.xticks(np.arange(0, 1.1, step=0.1))
#plt.show()
plt.savefig('export.png')