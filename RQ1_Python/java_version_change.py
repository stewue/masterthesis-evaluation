import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\javaversionchange.csv')

values = data['numberOfChanges']
valuesAll, base = np.histogram(values, bins=[0,1,2,3,4,5], weights=np.ones(len(values)) / len(values))
x = np.arange(5)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
label = ('0', '1', '2', '3', '4')
plt.bar(x, valuesAll)
plt.xticks(x, label)
plt.xlabel('numberOfJavaVersionChanges')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')