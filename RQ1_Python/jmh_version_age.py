import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter
data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionage.csv')
filtered = data[data['useJmhSince'] >= 1]
all = filtered['time']

valuesAll, base = np.histogram(all, bins=63, range=[0, 5.25], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)
plt.plot(base[:-1], cumulativeAll, label="all")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('ageOfJmhVersionInLastCommit [in years]')
plt.ylabel('cumulativePercentage')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')