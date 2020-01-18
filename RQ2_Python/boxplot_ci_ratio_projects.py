import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def convert(data):
    convered = data.to_numpy()
    return convered[np.logical_not(np.isnan(convered))]

type = 'ci'
dataMean = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplotciratio_' + type + '_mean.csv', delimiter=";")
dataMin = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplotciratio_' + type + '_min.csv', delimiter=";")
dataMax = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\boxplotciratio_' + type + '_max.csv', delimiter=";")

labels = []
mean = []
min = []
max = []
allMean = []
allMin = []
allMax = []

for column in dataMean.columns:
    labels.append(column)
    converted = convert(dataMean[column])
    mean.append(np.mean(converted))
    allMean.append(converted)

for column in dataMin.columns:
    converted = convert(dataMin[column])
    min.append(np.mean(converted))
    allMin.append(converted)

for column in dataMax.columns:
    converted = convert(dataMax[column])
    max.append(np.mean(converted))
    allMax.append(converted)

labels.append('all')
mean.append(np.mean(np.concatenate(allMean, axis=0)))
min.append(np.mean(np.concatenate(allMin, axis=0)))
max.append(np.mean(np.concatenate(allMax, axis=0)))

plt.errorbar(labels, mean, yerr=[np.subtract(mean, min),np.subtract(max, mean)], fmt='o', capsize=10)
plt.ylabel("CI ratio")
plt.xticks(rotation='vertical')
plt.tight_layout()
# plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\images\\boxplot_ciratio_projects_' + type + '.pdf')
