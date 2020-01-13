import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\codechanged.csv')
values = data['numberOfChanges']

valuesAll, base = np.histogram(values, bins=[0,1,2,3,4,5,9], weights=np.ones(len(values)) / len(values))
x = np.arange(6)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1,0))
label = ('0', '1', '2', '3', '4', '5-8')
plt.bar(x, valuesAll)
plt.ylim(0,0.09)
plt.xticks(x, label)
plt.xlabel('# code changes')
plt.ylabel('probability')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\code_changed.pdf')

somewhereChanged = values[values > 0]
print("at least one code change: " + str(somewhereChanged.size) + " (" + str(somewhereChanged.size / values.size) + "%)")
print("average code change: " + str(values.mean()))
print("std code change: " + str(values.std()))