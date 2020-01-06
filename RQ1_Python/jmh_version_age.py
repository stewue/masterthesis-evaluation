import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter
data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionage.csv')

all = data['time']

valuesAll, baseAll = np.histogram(all, bins=63, range=[0, 5.25], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)
plt.plot(baseAll[:-1], cumulativeAll, linestyle="-", label="all projects")

filteredOlder = data[data['useJmhSince'] > 1]
older = filteredOlder['time']

valuesOlder, baseOlder = np.histogram(older, bins=63, range=[0, 5.25], weights=np.ones(len(older)) / len(older))
cumulativeOlder = np.cumsum(valuesOlder)
plt.plot(baseOlder[:-1], cumulativeOlder, linestyle=":", label="long-lived projects")

filteredYounger = data[data['useJmhSince'] <= 1]
younger = filteredYounger['time']

valuesYounger, baseYounger = np.histogram(younger, bins=63, range=[0, 5.25], weights=np.ones(len(younger)) / len(younger))
cumulativeYounger = np.cumsum(valuesYounger)
plt.plot(baseYounger[:-1], cumulativeYounger, linestyle="--", label="short-lived projects")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('lag time of JMH version in last commit [years]')
plt.ylabel('cumulative probability')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')