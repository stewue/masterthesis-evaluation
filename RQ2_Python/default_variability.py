import pandas as pd
import numpy as np

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\shorteriterations\\defaultvariability.csv', delimiter=";")

print("---cov---")
print("avg: " + str(np.average(data['maxDeltaCov'])))
print("std: " + str(np.std(data['maxDeltaCov'])))
print("")
print("---ci width---")
filteredMaxCi = data[data['maxCi'] < 0.03]
print("absolute < 0.03: " + str(len(filteredMaxCi)/data.shape[0]))
filteredMaxDeltaCi = data[data['maxDeltaCi'] < 0.03]
print("change < 0.03: " + str(len(filteredMaxDeltaCi)/data.shape[0]))
print("")
print("---divergence---")
averageDivergence99 = data[data['averageDivergence'] > 0.99]
averageDivergence95 = data[data['averageDivergence'] > 0.95]
print("99%: " + str(len(averageDivergence99)/data.shape[0]))
print("95%: " + str(len(averageDivergence95)/data.shape[0]))