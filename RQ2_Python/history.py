import matplotlib.pyplot as plt

y = [0.25, 0.15, 0.16, 0.12, 0.09, 0.08, 0.06, 0.07, 0.05, 0.045, 0.052, 0.049, 0.052]
labels = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]

plt.scatter(labels, y)
plt.ylim(0, 0.3)
plt.xticks(labels)
plt.ylabel("stability metric")
plt.xlabel("# iteration")
plt.tight_layout()
# plt.show()
plt.savefig('export.pdf')