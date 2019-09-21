import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['measurementWarmupRatio'] = data['measurementWarmupRatio'].apply(pd.to_numeric, errors='coerce')
filtered = data[data['onlyModeChanged'] == False]
filtered = filtered[filtered['onlySingleShot'] == False]

smaller = filtered[filtered['measurementWarmupRatio'] < 1]
smallerAll = smaller['measurementWarmupRatio']
smallerProfessional = smaller[(smaller['group'] == 'PROFESSIONAL_FEW') | (smaller['group'] == 'PROFESSIONAL_MANY')]['measurementWarmupRatio']
smallerNonProfessional = smaller[(smaller['group'] == 'NOT_PROFESSIONAL_FEW') | (smaller['group'] == 'NOT_PROFESSIONAL_MANY')]['measurementWarmupRatio']
smallerArray = [smallerAll.values, smallerProfessional.values, smallerNonProfessional.values]
smallerWeights = [np.ones(len(smallerAll.values)) / len(smallerAll.values), np.ones(len(smallerProfessional.values)) / len(smallerProfessional.values), np.ones(len(smallerNonProfessional.values)) / len(smallerNonProfessional.values)]

larger = filtered[filtered['measurementWarmupRatio'] > 1]
largerAll = larger['measurementWarmupRatio']
largerProfessional = larger[(larger['group'] == 'PROFESSIONAL_FEW') | (larger['group'] == 'PROFESSIONAL_MANY')]['measurementWarmupRatio']
largerNonProfessional = larger[(larger['group'] == 'NOT_PROFESSIONAL_FEW') | (larger['group'] == 'NOT_PROFESSIONAL_MANY')]['measurementWarmupRatio']
largerArray = [largerAll.values, largerProfessional.values, largerNonProfessional.values]
largerWeights = [np.ones(len(largerAll.values)) / len(largerAll.values), np.ones(len(largerProfessional.values)) / len(largerProfessional.values), np.ones(len(largerNonProfessional.values)) / len(largerNonProfessional.values)]

colors = ['red', 'blue', 'green']
labels = ['All', 'Professional', 'Non professional']

plt.hist(largerArray, bins=20, range=[1,10], color=colors, weights=largerWeights, label=labels)
plt.xticks(np.arange(1, 10.1, step=1))

#plt.hist(smallerArray, bins=20, range=[0,1], color=colors, weights=smallerWeights)
#plt.xticks(np.arange(0, 1.1, step=0.1))

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.legend()
plt.ylabel('percentage')
plt.xlabel('measurementWarmupRatio')
plt.show()
#plt.savefig('export.png')