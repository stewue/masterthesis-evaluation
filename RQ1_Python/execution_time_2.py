import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['executionTimePercentage'] != 1]
x = filtered['executionTimePercentage']

plt.hist(x, bins=50, range=[0,1], weights=np.ones(len(x)) / len(x))
plt.xlabel('executionTimePercentage')
plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.show()
#plt.savefig('export.png')