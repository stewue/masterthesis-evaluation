import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import collections

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv',dtype='str')

prefilter = data[data['mainRepo'] == 'true']

print("number of projects " + str(prefilter.shape[0]))

javaVersion = np.where(prefilter['javaTarget'].isna(), prefilter['javaSource'], prefilter['javaTarget'])

values = javaVersion[pd.isnull(javaVersion) == False]

print("at least one set " + str(values.shape[0]))

counts = collections.Counter(values)

countOrdered = [counts["1.5"], counts["1.6"], counts["1.7"], counts["1.8"], counts["9"], counts["10"], counts["11"], counts["12"]]
ticks = ["1.5", "1.6", "1.7", "1.8", "9", "10", "11", "12"]
plt.bar(ticks, countOrdered)
plt.xlabel('JavaTarget Version')
plt.ylabel('Number of projects')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')
