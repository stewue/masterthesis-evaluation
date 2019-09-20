import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['executionTimePercentage'] != 1]
all = filtered['executionTimePercentage']
professional = filtered[(filtered['group'] == 'PROFESSIONAL_FEW') | (filtered['group'] == 'PROFESSIONAL_MANY')]['executionTimePercentage']
notProfessional = filtered[(filtered['group'] == 'NOT_PROFESSIONAL_FEW') | (filtered['group'] == 'NOT_PROFESSIONAL_MANY')]['executionTimePercentage']

valuesAll, base = np.histogram(all, bins=1000, range=[0,1], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)
plt.plot(base[:-1], cumulativeAll, label="all")

valuesProfessional, base = np.histogram(professional, bins=1000, range=[0,1], weights=np.ones(len(professional)) / len(professional))
cumulativeProfessional = np.cumsum(valuesProfessional)
plt.plot(base[:-1], cumulativeProfessional, color='red', label="professional")

valuesNotProfessional, base = np.histogram(notProfessional, bins=1000, range=[0,1], weights=np.ones(len(notProfessional)) / len(notProfessional))
cumulativeNotProfessional = np.cumsum(valuesNotProfessional)
plt.plot(base[:-1], cumulativeNotProfessional, color='green', label="non-professional")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('executionTimePercentage')
plt.ylabel('cumulativePercentage')
plt.xticks(np.arange(0, 1.1, step=0.1))
plt.legend()
#plt.show()
plt.savefig('export.png')