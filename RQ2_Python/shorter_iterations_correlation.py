import pandas as pd
import numpy as np
from scipy import stats

data = pd.read_csv('D:\\rq2\\pre\\mean.csv', delimiter=";")

abs = np.abs(data['percentage'] - 1)
corr = stats.spearmanr(abs, data['mean'])
print("p: " + str(corr.pvalue))
print("correlation: " + str(corr.correlation))

data2 = pd.read_csv('D:\\rq2\\pre\\combined.csv', delimiter=";")

corrCov = stats.spearmanr(data2['percentage'], data2['cov'])
print("COV")
print("p: " + str(corrCov.pvalue))
print("correlation: " + str(corrCov.correlation))

corrCi = stats.spearmanr(data2['percentage'], data2['ci'])
print("CI")
print("p: " + str(corrCi.pvalue))
print("correlation: " + str(corrCi.correlation))

corrDivergence = stats.spearmanr(data2['percentage'], data2['divergence'])
print("Divergence")
print("p: " + str(corrDivergence.pvalue))
print("correlation: " + str(corrDivergence.correlation))