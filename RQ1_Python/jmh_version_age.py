import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter
data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionage.csv')

all = data['time']

valuesAll, baseAll = np.histogram(all, bins=63, range=[0, 5.25], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)

filteredOlder = data[data['useJmhSince'] > 1]
older = filteredOlder['time']

valuesOlder, baseOlder = np.histogram(older, bins=63, range=[0, 5.25], weights=np.ones(len(older)) / len(older))
cumulativeOlder = np.cumsum(valuesOlder)

filteredYounger = data[data['useJmhSince'] <= 1]
younger = filteredYounger['time']

valuesYounger, baseYounger = np.histogram(younger, bins=63, range=[0, 5.25], weights=np.ones(len(younger)) / len(younger))
cumulativeYounger = np.cumsum(valuesYounger)

fig = plt.figure()
total = data.shape[0]

# absolute
ax1 = fig.add_subplot()
ax1.plot(baseAll[:-1], cumulativeAll * total, linestyle="-", label="all projects")
ax1.plot(baseOlder[:-1], cumulativeOlder * total, linestyle=":", label="long-lived projects")
ax1.plot(baseYounger[:-1], cumulativeYounger * total, linestyle="--", label="short-lived projects")
ax1.set_ylabel('# projects')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(baseAll[:-1], cumulativeAll, linestyle="-", label="all projects")
ax2.plot(baseOlder[:-1], cumulativeOlder, linestyle=":", label="long-lived projects")
ax2.plot(baseYounger[:-1], cumulativeYounger, linestyle="--", label="short-lived projects")
ax2.set_ylabel('# projects [cumulative %]')

ax1.set_xlabel('lag time of JMH version in last commit [years]')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\jmh_version_age.pdf')

o2 = older[older > 2]
print("older 2 years: " + str(len(o2) / len(older)))
print("older 2 years: " + str(len(o2)))